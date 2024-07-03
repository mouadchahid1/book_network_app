import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookStarsComponent } from './book-stars.component';

describe('BookStarsComponent', () => {
  let component: BookStarsComponent;
  let fixture: ComponentFixture<BookStarsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookStarsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookStarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
