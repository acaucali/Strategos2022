import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleProbabilidadComponent } from './detalle-probabilidad.component';

describe('DetalleProbabilidadComponent', () => {
  let component: DetalleProbabilidadComponent;
  let fixture: ComponentFixture<DetalleProbabilidadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleProbabilidadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleProbabilidadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
