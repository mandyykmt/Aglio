import { Component } from '@angular/core';
import { AccountService } from './account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    private accountService: AccountService
  ) {}

  isLoggedIn(): boolean {
    return this.accountService.isLoggedIn()
  }
  
  logout(): void {
    this.accountService.logout()
  }
}
