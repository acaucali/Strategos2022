import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EfectividadComponent } from './efectividad.component';

describe('EfectividadComponent', () => {
  let component: EfectividadComponent;
  let fixture: ComponentFixture<EfectividadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EfectividadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EfectividadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
