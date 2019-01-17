import { Component, OnInit } from '@angular/core';
import { PhoneModalService } from '../service/phoneModal.service';
import { AlternativeService } from '../service/alternative.service';
import { PhoneSubscription } from '../model/phoneSubscription.model';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-phone-modal',
  templateUrl: './phone-modal.component.html',
  styleUrls: ['./phone-modal.component.css']
})
export class PhoneModalComponent implements OnInit {

  private hidden: boolean = true;
  private phoneSubscriptions: PhoneSubscription[];
  private selectedId: number;

  phonesubscriptionform = this.fb.group({
    phoneSubscription: ['', Validators.required],
    preference: ['', Validators.required]
  });

  constructor(
    private phoneModalService: PhoneModalService,
    private alternativeService: AlternativeService,
    private router: Router,
    private fb: FormBuilder
  ) { }

  ngOnInit() {
    this.phoneModalService.getSubject().subscribe(hidden => this.hidden = hidden);
    this.alternativeService.getCurrentPhoneSubscription(1).subscribe(data => this.phoneSubscriptions = data);
  }


  close() {
    this.phoneModalService.close();
  }

  setSubscription(subscription: PhoneSubscription) {
    this.selectedId = subscription.id;
    this.phonesubscriptionform.patchValue({
      phoneSubscription: subscription
    });
  }

  onSubmit() {
    let subscription = <PhoneSubscription>this.phonesubscriptionform.value['phoneSubscription'];
    let preference = this.phonesubscriptionform.value['preference'];

    let internet = false;
    let sms = false;
    let minutes = false;

    switch (preference) {
      case "internet":
        internet = true;
        break;
      case "sms":
        sms = true;
        break;
      case "minutes":
        minutes = true;
        break;
      default:
        internet = true;
        break;
    }

    this.router.navigate(['/alternative'], {
      queryParams: {
        id: subscription.id.toString(),
        internet: String(internet),
        sms: String(sms),
        minutes: String(minutes)
      }
    })
  }
}
