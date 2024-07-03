import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-book-stars',
  templateUrl: './book-stars.component.html',
  styleUrl: './book-stars.component.scss'
})
export class BookStarsComponent {
   @Input() rating : number = 0 ;
   private maxRating : number = 5 ;

   fullStars():number { //4.5
     return Math.floor(this.rating); //4
   }
   hasHalfStar():boolean {
     return this.rating % 1 !==0;// yes
   }
   videStar():number {
     return this.maxRating - Math.ceil(this.rating);// 0
   }

}
