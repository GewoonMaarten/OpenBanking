import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlternativeService } from '../service/alternative.service';
import { PhoneSubscription } from '../model/phoneSubscription.model';
import { Page } from '../model/Page.model';

@Component({
  selector: 'app-alternative',
  templateUrl: './alternative.component.html',
  styleUrls: ['./alternative.component.css']
})
export class AlternativeComponent implements OnInit {


  private queryParams = {
    id: null,
    internet: null,
    minutes: null,
    sms: null
  }

  private phoneSubscriptions: Page<PhoneSubscription>;

  constructor(
    private route: ActivatedRoute,
    private alternativeService: AlternativeService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {

      this.queryParams = {
        id: params['id'] || 0,
        internet: params['internet'] || true,
        minutes: params['minutes'] || false,
        sms: params['sms'] || false
      };

      this.alternativeService.getAlternative(
        3,
        this.queryParams.id,
        this.queryParams.internet,
        this.queryParams.minutes,
        this.queryParams.sms
      ).subscribe(data => this.phoneSubscriptions = data);

    });
  }

}
