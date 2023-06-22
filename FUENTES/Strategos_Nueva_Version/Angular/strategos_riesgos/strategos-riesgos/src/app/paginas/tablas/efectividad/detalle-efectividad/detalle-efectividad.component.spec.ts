import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEfectividadComponent } from './detalle-efectividad.component';

describe('DetalleEfectividadComponent', () => {
  let component: DetalleEfectividadComponent;
  let fixture: ComponentFixture<DetalleEfectividadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleEfectividadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleEfectividadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
