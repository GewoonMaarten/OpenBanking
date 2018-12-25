import {Injectable, LOCALE_ID} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {formatDate} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class DebugService {

  constructor(private http: HttpClient) { }


  predictTransaction(amount: number, name: string, date: Date) {


  }
}
