import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigTareaComponent } from './config-tarea.component';

describe('ConfigTareaComponent', () => {
  let component: ConfigTareaComponent;
  let fixture: ComponentFixture<ConfigTareaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigTareaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigTareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
