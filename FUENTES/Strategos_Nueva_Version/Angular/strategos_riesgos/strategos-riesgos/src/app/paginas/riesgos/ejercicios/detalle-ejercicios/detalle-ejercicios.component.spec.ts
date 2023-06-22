import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEjerciciosComponent } from './detalle-ejercicios.component';

describe('DetalleEjerciciosComponent', () => {
  let component: DetalleEjerciciosComponent;
  let fixture: ComponentFixture<DetalleEjerciciosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleEjerciciosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleEjerciciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
