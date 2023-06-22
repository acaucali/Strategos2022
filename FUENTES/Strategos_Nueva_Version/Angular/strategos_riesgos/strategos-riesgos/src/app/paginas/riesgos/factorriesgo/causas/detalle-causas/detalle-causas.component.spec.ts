import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleCausasComponent } from './detalle-causas.component';

describe('DetalleCausasComponent', () => {
  let component: DetalleCausasComponent;
  let fixture: ComponentFixture<DetalleCausasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleCausasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleCausasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
