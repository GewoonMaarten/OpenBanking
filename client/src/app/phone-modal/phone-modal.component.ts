import { Component, OnInit } from '@angular/core';
import { PhoneModalService } from '../service/phoneModal.service';

@Component({
  selector: 'app-phone-modal',
  templateUrl: './phone-modal.component.html',
  styleUrls: ['./phone-modal.component.css']
})
export class PhoneModalComponent implements OnInit {

  private status: boolean;

  constructor(
    private phoneModalService: PhoneModalService
  ) { }

  ngOnInit() {
    this.phoneModalService.getStatus().subscribe(status => this.status = status);
  }

}
