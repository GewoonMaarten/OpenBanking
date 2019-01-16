import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Transaction } from '../model/transaction.model';
import { TransactionService } from '../service/transaction.service';
import { Page } from '../model/page.model';

@Component({
  selector: 'app-transaction-table',
  templateUrl: './transaction-table.component.html',
  styleUrls: ['./transaction-table.component.css']
})
export class TransactionTableComponent implements OnInit {

  private transactions: Page<Transaction> = null;
  private totalPagesList: number[];
  private currentPage: number;
  private totalPages: number;

  private loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private transactionService: TransactionService
  ) { }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {
      this.loading = true;

      this.currentPage = +params['page'] || 0;

      this.transactionService.getTransactions(1, this.currentPage - 1).subscribe(data => {
        this.transactions = data;
        this.totalPages = data.totalPages;
        this.totalPagesList = Array.from(Array(data.totalPages), (x,i) => i + 1);

        this.loading = false;
      });
    })


  }



}
