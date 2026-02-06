import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnotacionesList } from './anotaciones-list';

describe('AnotacionesList', () => {
  let component: AnotacionesList;
  let fixture: ComponentFixture<AnotacionesList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnotacionesList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnotacionesList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
