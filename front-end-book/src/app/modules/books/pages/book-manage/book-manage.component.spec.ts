import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookManageComponent } from './book-manage.component';

describe('BookManageComponent', () => {
  let component: BookManageComponent;
  let fixture: ComponentFixture<BookManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookManageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
