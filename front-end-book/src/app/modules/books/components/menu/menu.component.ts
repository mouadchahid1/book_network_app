import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../../services/services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  username!: string;

  ngOnInit(): void {
    this.activeRoute();
    this.username = this.authService.getUsernameFromToken();
  }

  constructor(private authService: AuthenticationService, private router: Router) {
  }


  logout() {
    localStorage.removeItem("token");
    this.router.navigate(["login"]);
  }

  activeRoute() {
    // je peux ajouter router Link active
    const linksActive = document.querySelectorAll("li a.nav-link");
    linksActive.forEach((link) => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {
        link.classList.add("active");
      }
      link.addEventListener("click", () => {
        linksActive.forEach((l) => {
          l.classList.remove("active");
          link.classList.add("active");
        })
      })
    })
  }
}


