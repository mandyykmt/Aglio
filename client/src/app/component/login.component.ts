import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;

  constructor ( 
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.form = this.createLogin()
  }

  createLogin(): FormGroup {
    return this.fb.group({
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(6), Validators.maxLength(15)])
    })
  }

  invalidInput(controlName: string): boolean {
    return !!(this.form.get(controlName)?.invalid && this.form.get(controlName)?.dirty)
  }

  // todo: route paths restricted by loginguard to appropriate paths
  login(): void {
    // this.accountService.email = this.form.get('email')?.value
    this.accountService.login(this.form.get('email')?.value, this.form.get('password')?.value)
                        .subscribe({
                          next: (successfulLogin) => {
                            alert("Login successful");
                            this.router.navigate(['/']);
                          },
                          error: (e) => {
                            if (e.status === 404) {
                              alert("Email not registered.")
                            } else if (e.status === 401) {
                              alert("Invalid password.")
                            }
                          }
                        })
  }
}
