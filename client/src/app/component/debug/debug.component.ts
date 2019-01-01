import {Component, LOCALE_ID, OnInit} from '@angular/core';
import { FormBuilder } from '@angular/forms';
import {HttpClient} from '@angular/common/http';

import {IsRecurringPrediction} from '../../model/isRecurringPrediction.model';
import {CategoryPrediction} from '../../model/categoryPrediction.model';
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

  isRecurring: IsRecurringPrediction;
  category: CategoryPrediction;
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
      this.transaction = data.category.transaction;
      this.category = data.category;
      this.isRecurring = data.isRecurring;

      // console.log(data.category);
    });
  }
}
