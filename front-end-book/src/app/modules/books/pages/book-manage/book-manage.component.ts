import {Component, OnInit} from '@angular/core';
import {BookRequest} from "../../../../services/models/book-request";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-book-manage',
  templateUrl: './book-manage.component.html',
  styleUrl: './book-manage.component.scss'
})
export class BookManageComponent implements OnInit {
  bookRequest: BookRequest = {authorName: "", isbn: "", shareable: false, title: "", synopsis: ""};
  bookCover: any;
  // abp document
  pictureSelected!: string;
  msgErrors: Array<string> = [];
  update  : boolean = false ;

  constructor(private bookService: BookService, private router: Router, private activeRouter: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loadBookById();
  }

  private loadBookById() {
    const id = this.activeRouter.snapshot.params["id"];
    if (id) {
      this.update = true;
      this.bookService.findBookById({
        "book_id": id,
      }).subscribe({
        next: (bookResponse) => {
          this.bookRequest = {
            id: bookResponse.id,
            isbn: bookResponse.isbn as string,
            synopsis: bookResponse.synopsis as string,
            title: bookResponse.title as string,
            authorName: bookResponse.authorName as string,
            shareable: bookResponse.shareable,
          }
          if (bookResponse.bookCover) {
            this.pictureSelected = "data:image/jpeg;base64," + bookResponse.bookCover;
          }
        }
      })
    }
  }


  randomImage() {
    return `https://picsum.photos/${Math.round(Math.random() * 400 + 1)}`;
  }

  // function pour lire et l'image et lui affiche dans le partie de l'image
  selectImage(image: any) {
    this.bookCover = image.target.files[0];
    if (this.bookCover) {
      const reader = new FileReader();// c est une variable dynamic par le array ;
      reader.onload = () => {
        this.pictureSelected = reader.result as string;
      }
      reader.readAsDataURL(this.bookCover);
    }
  }

  addBook() {
    this.bookService.saveBook({
      body: this.bookRequest
    }).subscribe({
      next: (bookId) => {
        this.bookService.uploadBookCover({
          "book_id": bookId,
          body: {
            file: this.bookCover
          }
        }).subscribe({
          next: () => {
            this.router.navigate(["books", "my-books"]);
          }
        })
      }, error: (err) => {
        this.msgErrors = err.error.validationErrors;
      }
    })
  }
}

// todo implement la partie de update and archive and shareabel
