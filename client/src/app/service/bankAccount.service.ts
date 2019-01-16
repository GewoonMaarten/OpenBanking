import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, merge } from 'rxjs';
import { BankAccount } from '../model/bankAccount.model';

@Injectable({
    providedIn: 'root'
})
export class BankAccountService {

    private baseUrl: string = 'http://localhost:8090/api';

    constructor(
        private http: HttpClient
    ){}

    getBankAccountsByUser(id: number): Observable<BankAccount[]> {
        return this.http.get<BankAccount[]>(this.baseUrl + '/bankAccount/user', {
            params: {
                id: id.toString()
            }
        });
    }

    getBankAccount (bankAccountId: number, date: Date): BankAccount{

        const day = ("0" + date.getDate()).slice(-2);
        const month = ("0" + (date.getMonth() + 1)).slice(-2);
        const dateString = `${day}/${month}/${date.getFullYear()}`;


        var bankAccount: BankAccount = new BankAccount();

        this.http.get(this.baseUrl + '/calculate/currentBalance?id=' + bankAccountId + "&date=" + dateString)
            .subscribe(data => bankAccount.balancechange = <number> data);

        this.http.get(this.baseUrl + '/calculate/expectedExpenses?id=' + bankAccountId + "&date=" + dateString)
            .subscribe(data => bankAccount.predictedBalanceChange = <number> data);

        this.http.get(this.baseUrl + '/calculate/expectedBalance?id=' + bankAccountId + "&date=" + dateString)
            .subscribe(data => bankAccount.predictedBalance = <number> data);

        this.http.get<BankAccount>(this.baseUrl + '/bankAccount/' + bankAccountId).subscribe(data => {
            bankAccount.iban = data.iban;
            bankAccount.balance = data.balance;
            bankAccount.id = data.id;
        })

        return bankAccount;
    }


}