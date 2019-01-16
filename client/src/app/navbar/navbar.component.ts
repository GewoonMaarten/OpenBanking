import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterStateSnapshot } from '@angular/router';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  currentRoute: string;

  constructor(
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.currentRoute = this.route.snapshot['_routerState'].url;
  }

}
