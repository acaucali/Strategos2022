import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEfectoComponent } from './detalle-efecto.component';

describe('DetalleEfectoComponent', () => {
  let component: DetalleEfectoComponent;
  let fixture: ComponentFixture<DetalleEfectoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleEfectoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleEfectoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
