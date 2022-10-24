import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallePerspectivaComponent } from './detalle-perspectiva.component';

describe('DetallePerspectivaComponent', () => {
  let component: DetallePerspectivaComponent;
  let fixture: ComponentFixture<DetallePerspectivaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetallePerspectivaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetallePerspectivaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
