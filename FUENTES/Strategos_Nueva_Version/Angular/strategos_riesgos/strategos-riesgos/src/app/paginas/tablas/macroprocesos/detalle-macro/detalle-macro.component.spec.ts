import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleMacroComponent } from './detalle-macro.component';

describe('DetalleMacroComponent', () => {
  let component: DetalleMacroComponent;
  let fixture: ComponentFixture<DetalleMacroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleMacroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleMacroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
