import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  ngOnInit(): void {
    this.activeRoute();
  }


  logout() {

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


