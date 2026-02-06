import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAnotaciones } from './admin-anotaciones';

describe('AdminAnotaciones', () => {
  let component: AdminAnotaciones;
  let fixture: ComponentFixture<AdminAnotaciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAnotaciones]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAnotaciones);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
