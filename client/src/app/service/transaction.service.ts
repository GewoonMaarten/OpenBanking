import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../model/Page.model';
import { Transaction } from '../model/transaction.model';

@Injectable({
    providedIn: 'root'
})
export class TransactionService {

    private baseUrl: string = 'http://localhost:8090/api';

    constructor(
        private http: HttpClient
    ){}

    getTransactionsBeforeDate(bankAccountId: number, date: Date, size: number = 10): Observable<Page<Transaction>> {
        return this.http.get<Page<Transaction>>(this.baseUrl + '/transactions', {
            params: {
                bankAccountId: bankAccountId.toString(),
                date: this.dateToString(date),
                size: size.toString(),
                sort: 'date,desc'
            }
        });
    }

    getTransactions(bankAccountId: number, page: number = 0): Observable<Page<Transaction>> {
        return this.http.get<Page<Transaction>>(this.baseUrl + '/transactions', {
            params : {
                bankAccountId: bankAccountId.toString(),
                page: page.toString(),
                sort: 'date,desc'
            }
        });
    }

    private dateToString(date: Date): string {
        const day = ("0" + date.getDate()).slice(-2);
        const month = ("0" + (date.getMonth() + 1)).slice(-2);
        return `${day}/${month}/${date.getFullYear()}`;
    }
}