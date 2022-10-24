import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleIndicadorComponent } from './detalle-indicador.component';

describe('DetalleIndicadorComponent', () => {
  let component: DetalleIndicadorComponent;
  let fixture: ComponentFixture<DetalleIndicadorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleIndicadorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleIndicadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
