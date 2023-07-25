import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { FindComponent } from './component/find.component';
import { HomeComponent } from './component/home.component';
import { ListingComponent } from './component/listing.component';
import { LoginComponent } from './component/login.component';
import { RegisterComponent } from './component/register.component';
import { UpsertComponent } from './component/upsert.component';
import { DonateComponent } from './component/donate.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { NgxStripeModule } from 'ngx-stripe';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button'; 
import { MatCardModule } from '@angular/material/card'; 
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table'; 
import { MatToolbarModule } from '@angular/material/toolbar';

import { AccountService } from './account.service';
import { ListingService } from './listing.service';
import { loginGuard } from './util';
import { DonateService } from './donate.service';
import { SearchComponent } from './component/search.component';

const appRoutes: Routes = [
  { path: "", component:HomeComponent },
  { path: "home", component:HomeComponent },
  { path: "find", component:FindComponent, canActivate: [loginGuard] },
  { path: "find/:imageKey", component:ListingComponent, canActivate: [loginGuard] },
  { path: "search", component:SearchComponent, canActivate: [loginGuard]},
  { path: "register", component:RegisterComponent},
  { path: "login", component:LoginComponent },
  { path: "add", component:UpsertComponent, canActivate: [loginGuard] },
  { path: "donate", component:DonateComponent },
  { path: "**", redirectTo: '/', pathMatch: 'full' }
]

const stripeKey: string = 'pk_test_51NXG0qDSjy6Rh7CGq0VMA6wYaB52ZH3A993ZZkJT82GggybFNIj31fyJRRvZaU4q37HlB933w602J0Wh6pNLyBsC00JLAOmxDp'

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FindComponent,
    UpsertComponent,
    RegisterComponent,
    LoginComponent,
    ListingComponent,
    DonateComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    NgxStripeModule.forRoot(stripeKey),
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatStepperModule,
    MatTableModule,
    MatToolbarModule,
    BrowserAnimationsModule
  ],
  providers: [
    AccountService,
    ListingService,
    DonateService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
