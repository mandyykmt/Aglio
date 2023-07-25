import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Stripe, StripeCardElement, StripeElements, loadStripe } from '@stripe/stripe-js';
import { DonateService } from '../donate.service';
import { DonationData } from '../models';
import { Router } from '@angular/router';

const stripeKey: string = 'pk_test_51NXG0qDSjy6Rh7CGq0VMA6wYaB52ZH3A993ZZkJT82GggybFNIj31fyJRRvZaU4q37HlB933w602J0Wh6pNLyBsC00JLAOmxDp'

@Component({
  selector: 'app-donate',
  templateUrl: './donate.component.html',
  styleUrls: ['./donate.component.css']
})
export class DonateComponent implements OnInit, AfterViewInit{

  @ViewChild('cardElement') cardElement!: ElementRef;

  stripePromise!: Promise<Stripe | null>;
  elements!: StripeElements;
  card!: StripeCardElement;
  form!: FormGroup;

  constructor(
    private fb: FormBuilder, 
    private donateService: DonateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  ngAfterViewInit(): void {
    this.loadStripe()
  }

  async loadStripe(): Promise<void> {

    this.stripePromise = loadStripe(stripeKey)

    const stripe = await this.stripePromise;

    if (stripe) {
      this.elements = stripe.elements();
      this.card = this.elements.create('card', { hidePostalCode: true });
      this.card.mount(this.cardElement.nativeElement);

    } else {
      console.error('Error loading Stripe.');
    }
  }

  createForm() {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      amount: this.fb.control<number | string>('', [Validators.required, Validators.pattern('^[0-9]+$')])
    })
  }

  postDonation(): void {

    const donationData: DonationData = {
      name: this.form.get('name')?.value,
      amount: this.form.get('amount')?.value
    }

    this.donateService.postDonation(donationData).subscribe({
      next: (data) => {
        const clientSecret = data.pi;
        this.stripePromise.then((stripe) => {
          if (!stripe) {
            alert('Stripe not loaded.');
            return;
          }
          stripe.confirmCardPayment(clientSecret, {
            payment_method: {
              card: this.card
            }
          })
          .then(({ paymentIntent, error }) => {
            if (error) {
              alert(error);
            } else {
              alert('Payment successful. Thank you, ' + donationData.name);
              console.log(paymentIntent);
              this.router.navigate(['/'])
            }
          });
        })
        .catch((error) => {
          alert('Error loading Stripe: ' + error);
        });
      },
      error: (e) => {
        alert('Error creating PaymentIntent: ' + e);
      }
    });
  }
}
