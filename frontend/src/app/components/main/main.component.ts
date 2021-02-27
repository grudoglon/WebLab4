import {Component, OnInit, ViewChild} from '@angular/core';
import { NotifierService } from "angular-notifier";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';
import {GraphComponent} from "./graph/graph.component";
import {PointService} from "../../services/point.service";
import {Router} from "@angular/router";
declare var $:any;

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  @ViewChild(GraphComponent) graphComponent:GraphComponent;
  @ViewChild("customNotification", { static: true }) customNotificationTmpl;
  private readonly notifier: NotifierService;
  mainForm: FormGroup;
  submitted = false;

  xInputOnChange: boolean = false;
  yInputOnFocus: boolean = false;
  rInputOnChange: boolean = false;


  constructor(notifierService: NotifierService, private formBuilder: FormBuilder, private pointService: PointService, private router: Router) {
    this.notifier = notifierService;

  }

  get f() { return this.mainForm.controls; }

  sendPoint(data){
    this.pointService.addPoint(data).subscribe(resp => {
      if(resp.data.hit) {
        this.showMessage("Point is hit", "default", resp.data.id)
      }else{
        this.showMessage("Point isn't hit", "error", resp.data.id)
      }

      this.pointService.getPoints().subscribe(data =>{
        this.graphComponent.loadPoints(data.points);
      });
    }, err=>{
      this.notifier.notify("error", "Out of graph")
    });
  }

  submit() {
    this.submitted = true;

    if (this.mainForm.invalid) {
      return;
    }

    let req = this.mainForm.value;
    req.y = req.y.replace(",", ".");
    this.updateAllValues();
    this.sendPoint(req);
  }

  ngOnInit(): void {
    this.mainForm = this.formBuilder.group({
      x: ['', Validators.required],

      y: ['', [
        Validators.required, Validators.min(-5),
        Validators.max(3),
        Validators.pattern(/^[-+]?[0-9]*[.,]?[0-9]+$/) ]
      ],

      r: ['', [Validators.required, Validators.min(1)]]
    });

    this.pointService.getPoints().subscribe(data =>{
      this.graphComponent.loadPoints(data.points);
    });

  }

  onGraphEvent(data){
    this.sendPoint(data);
  }


  showMessage(message, type, id){
    this.notifier.show({
      message: message,
      type: type,
      template: this.customNotificationTmpl,
      id: id
    });
  }

  valueXChange($event) {
    if (!$event.target.checked) return;
    this.xInputOnChange = true;

    this.updateAllValues();
  }

  valueYChange($event) {
    if(this.f.y.invalid) return;

    this.updateAllValues();
  }

  valueRChange($event) {
    if (!$event.target.checked) return;
    this.rInputOnChange = true;

    this.updateAllValues();
  }


  updateAllValues(){
    // if(this.f.x.value != null && this.f.y.value != null && this.f.r.value != null){
      this.graphComponent.XValue = this.f.x.value;
      this.graphComponent.YValue = this.f.y.value;
      this.graphComponent.rawRValue = this.f.r.value;
      this.graphComponent.setCrossings();

  }

  onClear() {
    this.f.x.reset();
    this.f.y.reset();
    this.f.r.reset();

    this.xInputOnChange = this.yInputOnFocus = this.rInputOnChange = this.submitted = false;

    this.updateAllValues();
  }

  onNotifyClick(id) {
    this.router.navigate(['/history', id]);
  }
}
