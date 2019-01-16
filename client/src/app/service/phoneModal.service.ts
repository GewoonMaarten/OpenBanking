import {Injectable} from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhoneModalService {

  private status: BehaviorSubject<boolean> = new BehaviorSubject(true);

  constructor(){ }

  toggle() {
    console.log(this.status.getValue());

    this.status.next(!this.status.getValue());
  }

  getStatus(): BehaviorSubject<boolean> {
    return this.status;
  }
}
