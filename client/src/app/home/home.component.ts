import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';

import { BankAccount } from '../model/bankAccount.model';
import { BankAccountService } from "../service/bankAccount.service";
import { OutlierService } from '../service/outlier.service';
import { Transaction } from '../model/transaction.model';
import { PhoneModalService } from '../service/phoneModal.service';
import { IconService } from '../service/icon.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  bankAccount: BankAccount = null;
  bankAccounts: BankAccount[] = null;

  queryParams = {
    userId: null,
    bankAccountId: null,
    date: null
  };

  months = [
    {name: 'januari', id: 0},
    {name: 'februari', id: 1},
    {name: 'maart', id: 2},
    {name: 'april', id: 3},
    {name: 'mei', id: 4},
    {name: 'juni', id: 5},
    {name: 'juli', id: 6},
    {name: 'augustus', id: 7},
    {name: 'september', id: 8},
    {name: 'oktober', id: 9},
    {name: 'november', id: 10},
    {name: 'december', id: 11}
  ];

  years = [
    2019,
    2018,
    2017,
    2016
  ]

  month: string;
  year: number;

  elevatedRecurringTransactions: Transaction[] = [];
  elevatedOtherTransactions: Transaction[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bankAccountService: BankAccountService,
    private outlierService: OutlierService,
    private phoneModalService: PhoneModalService,
    private iconService: IconService
  ) { }

  ngOnInit() {

  
    this.route.queryParams.subscribe(params => {

      this.phoneModalService.close();

      this.elevatedRecurringTransactions = [];
      this.elevatedOtherTransactions = [];

      this.queryParams.userId = params['userId'] || 1;
      this.queryParams.bankAccountId = params['bankAccountId'] || 1;

      let date = params['date'] || null;

      if(date) {
        let parts = date.split('/');
        date = new Date(parts[2], parts[1] , 1);
      } else {
        date = new Date();
      }

      this.queryParams.date = date;

      this.month = this.months.filter(month => {
        return month.id == date.getMonth();
      })[0].name;

      this.year = date.getFullYear(); 

      this.bankAccountService.getBankAccountsByUser(this.queryParams.userId)
      .subscribe(bankAccounts => {
        this.bankAccounts = bankAccounts;
      });

      this.bankAccount = this.bankAccountService.getBankAccount(this.queryParams.bankAccountId,  this.queryParams.date);

      let dateEnd = <Date> this.queryParams.date;
      let dateStart = new Date(dateEnd.getTime());

      dateStart.setMonth(dateEnd.getMonth() - 3);

      this.outlierService.getOutlier(
        this.queryParams.bankAccountId,
        false,
        "overigeUitgaven",
        dateStart,
        dateEnd,
        1
      ).subscribe(data => {
        this.elevatedOtherTransactions = data.content;
      });


      this.outlierService.getOutlier(
        this.queryParams.bankAccountId,
        true,
        "telecom",
        dateStart,
        dateEnd,
        .7
      ).subscribe(data => {
        this.elevatedRecurringTransactions = data.content;
      });
    });
    
  }

  setBankAccount(bankAccount: BankAccount) {
    let navigationExtras: NavigationExtras = {
      queryParams: {...this.queryParams, bankAccountId: bankAccount.id}
    };
    this.router.navigate(['/home'], navigationExtras);
  }

  setMonth(month: number) {
    let date = <Date> this.queryParams.date;

    date.setMonth(month);

    let navigationExtras: NavigationExtras = {
      queryParams: {...this.queryParams, date: this.dateToString(date)}
    };
    this.router.navigate(['/home'], navigationExtras);
  }

  setYear(year: number) {
    let date = <Date> this.queryParams.date;

    date.setFullYear(year);

    let navigationExtras: NavigationExtras = {
      queryParams: {...this.queryParams, date: this.dateToString(date)}
    };
    this.router.navigate(['/home'], navigationExtras);
  }
  
  openModal() {
    console.log("Toggled!");
    this.phoneModalService.toggle();
  }

  private dateToString(date: Date): string {
    const day = ("0" + date.getDate()).slice(-2);
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    return `${day}/${month}/${date.getFullYear()}`;
  }

  private getIcon(category: string): string {
    return this.iconService.getIconByCategory(category);
  }
}
