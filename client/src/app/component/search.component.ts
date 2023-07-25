import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Params, Router } from '@angular/router';
import { ListingService } from '../listing.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form!: FormGroup;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private listingService: ListingService
  ) {}

  ngOnInit(): void {
      this.form = this.createForm()
  }

  createForm(): FormGroup {
    return this.fb.group({
      keyword: this.fb.control<string>('', [Validators.required])
    })
  }

  search() {
    this.listingService.getListingsByKeyword(this.form.get('keyword')?.value)
                        .subscribe({
                          next: (response) => {
                            console.info(response);
                            const queryParams : Params = {search : this.form.get('keyword')?.value}
                            this.router.navigate(['/find'], { queryParams: queryParams})
                          },
                          error: (error) => {
                            alert("No data found. Why not contribute?")
                            this.router.navigate(['/add'])
                          }
                        })
  }

  clearForm(): void {
    this.form.reset()
  }
}
