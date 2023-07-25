import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../account.service';
import { User } from '../models';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
      this.form = this.createRegistration()

      this.form.get('0.password')?.valueChanges.subscribe(() => {
        this.form.get('0.password2')?.updateValueAndValidity();
      })
  }

  createRegistration(): FormGroup {
    return this.fb.group({
      0: this.createUsername(),
      1: this.createUser(),
      2: this.createContact()
    })
  }

  createUsername(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(15)]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(6), Validators.maxLength(15)]),
      password2: this.fb.control<string>('', [Validators.required, this.checkPassword()])
    })
  }

  createUser(): FormGroup {
    return this.fb.group({
      firstName: this.fb.control<string>('', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      lastName: this.fb.control<string>('', [Validators.required, Validators.minLength(2), Validators.maxLength(20)])      
    })
  }

  createContact(): FormGroup {
    return this.fb.group({
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      phone: this.fb.control<string>('', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]),
      country: this.fb.control<string>('', [Validators.required]),
      postalCode: this.fb.control<string>('', [Validators.required, Validators.minLength(6), Validators.maxLength(6)])
    })
  }

  checkPassword(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.parent?.get('password')?.value;
      const password2 = control.value;
  
      if (password !== password2) {
        return { passwordMismatch: true };
      }
      return null;
    };
  }

  getFormGroup(formGroupName: string): FormGroup {
    return this.form.get(formGroupName) as FormGroup
  }

  clearForm(): void {
    this.form.reset()
  }

  invalidForm(): boolean {
    return this.form.invalid || (this.form.get('password')?.value != this.form.get('password2')?.value)
  }

  submitRegistration(): void {

    console.info(">> form: ", this.form.value)

    const user: User = {
      username: this.form.get('0.username')?.value,
      password: this.form.get('0.password')?.value,
      password2: this.form.get('0.password2')?.value,
      firstName: this.form.get('1.firstName')?.value,
      lastName: this.form.get('1.lastName')?.value,
      email: this.form.get('2.email')?.value,
      phone: this.form.get('2.phone')?.value,
      country: this.form.get('2.country')?.value,
      postalCode: this.form.get('2.postalCode')?.value
    }

    console.info(">> formUser: ", user)

    this.accountService.register(user)
      .subscribe({
          next: (succesfulRegistration) => {
            alert("Registration successful.");
            this.router.navigate(['/login']);
          },
          error: (e) => {
            // alert(JSON.stringify(e.error)) works as well
            alert("Email already registed. Please check your input.");
          },
          complete: () => {}
        });
  }
}