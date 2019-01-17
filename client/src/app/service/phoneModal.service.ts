import {Injectable} from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhoneModalService {

  private hidden: BehaviorSubject<boolean> = new BehaviorSubject(true);

  constructor(){ }

  toggle() {
    this.hidden.next(!this.hidden.getValue());
  }

  getSubject(): BehaviorSubject<boolean> {
    return this.hidden;
  }

  open() {
    this.hidden.next(false);
  }

  close() {
    this.hidden.next(true);
  }
}
