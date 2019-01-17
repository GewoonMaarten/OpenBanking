import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Page } from '../model/Page.model';
import { PhoneSubscription } from '../model/phoneSubscription.model';
import { Transaction } from '../model/transaction.model';

@Injectable({
    providedIn: 'root'
})
export class AlternativeService {

    private baseUrl: string = 'http://localhost:8090/api';

    constructor(
        private http: HttpClient
    ){}

    getCurrentPhoneSubscription(bankAccountId: number): Observable<PhoneSubscription[]> {
        return this.http.get<PhoneSubscription[]>(this.baseUrl + '/phone/byBankAccount', {
            params: {
                bankAccountId: bankAccountId.toString()
            }
        })
    }

    getAlternative(
        size: number, 
        id: number, 
        internet: boolean = true, 
        minutes: boolean = false, 
        sms: boolean = false): Observable<Page<PhoneSubscription>> {

        return this.http.get<Page<PhoneSubscription>>(this.baseUrl + '/phone/alternative', {
            params: {
                size: size.toString(),
                id: id.toString(),
                internet: String(internet),
                minutes: String(minutes),
                sms: String(sms)
            }
        });
    }

}