import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookResponse} from "../../../../services/models/book-response";


@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {
  private _book: BookResponse = {};
  private _bookCover: string | undefined;
  private _manage: boolean = false;
  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }


  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }

  get bookCover(): string | undefined {
    if (this.book.bookCover) {
      return "data:image/jpeg;base64," + this.book.bookCover;
    } else {
      return `https://picsum.photos/${Math.round(Math.random() * 400 + 1)}`;
    }
  }

  @Output() private details = new EventEmitter<BookResponse>;
  @Output() private borrow = new EventEmitter<BookResponse>;
  @Output() private addToWaitingList = new EventEmitter<BookResponse>;
  @Output() private edit = new EventEmitter<BookResponse>;
  @Output() private share = new EventEmitter<BookResponse>;
  @Output() private archive = new EventEmitter<BookResponse>;


  onShowDetails() {
    this.details.emit(this.book);
  }

  onBorrow() {
    this.borrow.emit(this.book);
  }

  onAddToWaitingList() {
    this.addToWaitingList.emit(this.book);
  }

  onEdit() {
    this.edit.emit(this.book);
  }

  onShare() {
    this.share.emit(this.book);
  }

  onArchive() {
    this.archive.emit(this.book);
  }
}
