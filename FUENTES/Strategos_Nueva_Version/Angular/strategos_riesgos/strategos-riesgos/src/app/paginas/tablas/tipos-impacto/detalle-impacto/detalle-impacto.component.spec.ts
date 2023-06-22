import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleImpactoComponent } from './detalle-impacto.component';

describe('DetalleImpactoComponent', () => {
  let component: DetalleImpactoComponent;
  let fixture: ComponentFixture<DetalleImpactoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleImpactoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleImpactoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
