import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { ReactiveFormsModule, FormsModule  } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import localeNL from "@angular/common/locales/nl";

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DebugComponent } from './debug/debug.component';
import { HomeComponent } from './home/home.component';
import { TransactionTableComponent } from './transaction-table/transaction-table.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AlternativeComponent } from './alternative/alternative.component';
import { PhoneModalComponent } from './phone-modal/phone-modal.component';

import { AppRoutingModule } from "./app-routing.module";

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { 
  faSignOutAlt, 
  faUser, 
  faForward, 
  faClock, 
  faInfoCircle, 
  faShoppingBag, 
  faUtensils, 
  faHome, 
  faGraduationCap, 
  faCoins, 
  faTshirt, 
  faStethoscope, 
  faEllipsisH, 
  faPhone, 
  faCar, 
  faShieldAlt, 
  faFutbol,
  faUndo
} from "@fortawesome/free-solid-svg-icons";
import { registerLocaleData } from '@angular/common';

//import { faClock } from "@fortawesome/free-regular-svg-icons";

registerLocaleData(localeNL);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DebugComponent,
    HomeComponent,
    TransactionTableComponent,
    NavbarComponent,
    AlternativeComponent,
    PhoneModalComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule,
    AppRoutingModule
  ],
  providers: [{provide: LOCALE_ID, useValue: 'nl'}],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor() {
    library.add(
      faSignOutAlt,
      faUser,
      faClock,
      faForward,
      faInfoCircle,
      faShoppingBag, 
      faUtensils, 
      faHome, 
      faGraduationCap, 
      faCoins, 
      faTshirt, 
      faStethoscope, 
      faEllipsisH, 
      faPhone, 
      faCar, 
      faShieldAlt, 
      faFutbol,
      faUndo
    )
  }
}
