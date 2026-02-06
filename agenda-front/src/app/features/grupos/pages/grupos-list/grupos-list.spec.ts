import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GruposList } from './grupos-list';

describe('GruposList', () => {
  let component: GruposList;
  let fixture: ComponentFixture<GruposList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GruposList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GruposList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
