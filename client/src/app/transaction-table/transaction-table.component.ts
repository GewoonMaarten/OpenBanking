import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Transaction } from '../model/transaction.model';
import { TransactionService } from '../service/transaction.service';
import { Page } from '../model/page.model';
import { IconService } from '../service/icon.service';

@Component({
  selector: 'app-transaction-table',
  templateUrl: './transaction-table.component.html',
  styleUrls: ['./transaction-table.component.css']
})
export class TransactionTableComponent implements OnInit {

  groupedTransactionsList: GroupedTransactions[] = null;

  constructor(
    private route: ActivatedRoute,
    private transactionService: TransactionService,
    private iconService: IconService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {

      let date = params['date'] || null;
      let bankAccountId = params['bankAccountId'] || 1;

      if(date) {
        let parts = date.split('/');
        date = new Date(parts[2], parts[1] - 1, parts[0]);
      } else {
        date = new Date();
      }

      let t: GroupedTransactions[] = [];

      this.transactionService
        .getTransactionsBeforeDate(bankAccountId, date, 10)
        .subscribe(page => {
          page.content.forEach(transaction => {
            if (Object.keys(transaction).length === 0 && transaction.constructor === Object) return;

            let date: any = transaction.date;

            if(date) {
              let parts = date.split('/');
              date = new Date(+parts[2], +parts[1] - 1, +parts[0]);
            } else {
              date = new Date();
            }

            let today = new Date();
            let yesterday = new Date();
            yesterday.setDate(yesterday.getDate() - 1);

            if(
              date.getDate() === today.getDate() &&
              date.getMonth() === today.getMonth() &&
              date.getFullYear() === today.getFullYear()
            ) {
              t.filter(obj => obj['date'] == 'Vandaag').length == 0 ? t.push({date: 'Vandaag', transactions: []}) : null;
              t.forEach(obj => obj['date'] == 'Vandaag' ? obj['transactions'].push(transaction) : null);
            } else if (
              date.getDate() === yesterday.getDate() &&
              date.getMonth() === yesterday.getMonth() &&
              date.getFullYear() === yesterday.getFullYear()
            ) {
              t.filter(obj => obj['date'] == 'Gisteren').length == 0 ? t.push({date: 'Gisteren', transactions: []}) : null;
              t.forEach(obj => obj['date'] == 'Gisteren' ? obj['transactions'].push(transaction) : null);
            } else {
              let dateString = this.dateToString(date);

              t.filter(obj => obj['date'] == dateString).length == 0 ? t.push({date: dateString, transactions: []}) : null;
              t.forEach(obj => obj['date'] == dateString ? obj['transactions'].push(transaction) : null);
            }
          });

          this.groupedTransactionsList = t;
        });
        
    });
  }

  private dateToString(date: Date): string {
    let months = [
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

    let month = months.filter(month => {
      return month.id == date.getMonth();
    })[0].name;

    return `${date.getDate() + 1} ${month} ${date.getFullYear()}`
  }

  getIcon(category: string): String{
    return this.iconService.getIconByCategory(category);
  }

}

class GroupedTransactions {
  date: string;
  transactions: Transaction[];
}