import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule  } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DebugComponent } from './debug/debug.component';
import { HomeComponent } from './home/home.component';
import { TransactionTableComponent } from './transaction-table/transaction-table.component';
import { NavbarComponent } from './navbar/navbar.component';

import { AppRoutingModule } from "./app-routing.module";

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faSignOutAlt, faUser, faForward, faClock, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import { AlternativeComponent } from './alternative/alternative.component';
//import { faClock } from "@fortawesome/free-regular-svg-icons";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DebugComponent,
    HomeComponent,
    TransactionTableComponent,
    NavbarComponent,
    AlternativeComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor() {
    library.add(
      faSignOutAlt,
      faUser,
      faClock,
      faForward,
      faInfoCircle
    )
  }
}
