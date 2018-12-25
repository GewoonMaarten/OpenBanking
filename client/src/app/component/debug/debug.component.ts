import {Component, LOCALE_ID, OnInit} from '@angular/core';
import { FormBuilder } from '@angular/forms';
import {HttpClient} from '@angular/common/http';

import {DebugService} from '../../service/debug.service';
import {formatDate} from '@angular/common';
import {Transaction} from '../../model/transaction.model';

@Component({
  selector: 'app-debug',
  templateUrl: './debug.component.html',
  styleUrls: ['./debug.component.css']
})
export class DebugComponent implements OnInit {

  transactionForm = this.fb.group({
    amount: [''],
    name: [''],
    date: ['']
  });

  transaction: Transaction;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) { }

  ngOnInit() {
  }


  onSubmit() {


    const date = new Date(this.transactionForm.get('date').value);

    console.log(date);
    const dateString = `${date.getDay()}/${date.getMonth()}/${date.getFullYear()}`;
    this.http.get('http://localhost:8090/api/predict/all', {
      params: {
        amount: this.transactionForm.get('amount').value,
        name: this.transactionForm.get('name').value,
        date: dateString
      }
    }).subscribe(data => {
      this.transaction = <Transaction> data;
    });
  }
}
