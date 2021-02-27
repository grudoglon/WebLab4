import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {DOCUMENT} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(authService: AuthService, router: Router) {
    if (authService.isLoggedIn()) {
      router.navigate(['']);
    }
  }


  ngOnInit(): void {
  }

}
