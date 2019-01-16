import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction.model';
import { Page } from '../model/Page.model';


@Injectable({
  providedIn: 'root'
})
export class OutlierService {

  private baseUrl: string = 'http://localhost:8090/api';

  constructor(
    private http: HttpClient
  ){}


  getOutlier(bankAccountId: number, isRecurring: boolean, category: string, dateStart: Date, dateEnd: Date, threshold: number): Observable<Page<Transaction>> {

    return this.http.get<Page<Transaction>>(this.baseUrl + '/transactions/outliers', {
      params: {
        bankAccountId: bankAccountId.toString(),
        category: category,
        isRecurring: String(isRecurring),
        dateStart: this.dateToString(dateStart),
        dateEnd: this.dateToString(dateEnd),
        threshold: threshold.toString()
      }
    });
  }

  private dateToString(date: Date): string {
    const day = ("0" + date.getDate()).slice(-2);
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    return `${day}/${month}/${date.getFullYear()}`;
  }
}
