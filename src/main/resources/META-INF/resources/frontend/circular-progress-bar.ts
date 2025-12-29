import { css, html, LitElement } from 'lit';
import { customElement, property, query } from 'lit/decorators.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import { TooltipController } from '@vaadin/component-base/src/tooltip-controller.js';

@customElement('circular-progress-bar')
export class CircularProgressBar extends ThemableMixin(LitElement) {

	@property()
	scale = 1.0;

	@property()
	percent = 0;

	@property()
	delay = 10;

	@property()
	label : string | null = null;

  	@property({reflect: true})
  	animation : boolean | undefined = undefined;

	@query("#animated")
	circularProgressbar! : SVGElement;

    _tooltipController : TooltipController | undefined;

    // This is needed just for ThemableMixin
    static get is() {
        return 'circular-progress-bar';
    }

	static get styles() {
    	return css`
            :host {
                --_circle-width: var(--circle-width, var(--progress-circle-width, 6));
                --_circle-inner-width: var(--circle-inner-width, calc(var(--_circle-width) - 2));
				--_circle-color: var(--circle-color, var(--lumo-primary-color, hsl(214, 100%, 48%)));
				--_circle-background-color: var(--circle-background-color, var(--lumo-contrast-20pct, hsla(214, 53%, 23%, 0.16)));
				--_circle-border-color: var(--circle-border-color, var(--lumo-contrast, hsl(214, 35%, 15%)));
                --_caption-font-size: var(--circle-font-size, var(--progress-caption-font-size, 11));
                --_percent-font-size: var(--percent-font-size, var(--progress-percent-font-size, 17));
            }
    		svg #count {
        		fill: var(--percent-text-color, var(--lumo-primary-text-color, hsl(214, 100%, 43%)));
    		}
    		svg #caption {
        		fill: var(--caption-text-color, var(--lumo-secondary-text-color, hsla(214, 42%, 18%, 0.69)));
    		}
			:host([noborder]) svg #progress-border {
				display: none;
			}
			:host([noborder]) svg #progress-inner {
				stroke-width: var(--_circle-width);
			}
		`;
	}

    firstUpdated() {
	   this._tooltipController = new TooltipController(this, 'tooltip');
       this.addController(this._tooltipController);
    }

	updated() {
		if (this.label) {
			this.updateCaption(this.label);
		}
		if (this.animation) {
		    this.animateProgress(this.percent, this.scale);
		} else {
		    this.setProgress(this.percent, this.scale);			
		}
	}

	private updateCaption(captionText : string) : void {
        const countElement = this.circularProgressbar.querySelector('#count');
        countElement?.setAttribute('y', '43');
        const captionElement = this.circularProgressbar.querySelector('#caption');
        if (!captionElement) {
            var svg = this.circularProgressbar.querySelector('#animated');
            this.circularProgressbar.innerHTML = this.circularProgressbar.innerHTML + '<text id="caption" part="caption" x="50" y="59" text-anchor="middle" dy="7" font-size="var(--_caption-font-size)">' + captionText + '</text>';
        } else {
            captionElement.textContent = captionText;
        }
    }

	private setProgress(percent : number, scale : number) {
        
        const progressBorder = this.circularProgressbar.querySelector('#progress-border');
        const progressInner = this.circularProgressbar.querySelector('#progress-inner');
        const text = this.circularProgressbar.querySelector('#count');

        progressBorder?.setAttribute('stroke-dasharray', 251.2 * percent * scale + ',251.2');
        progressInner?.setAttribute('stroke-dasharray', 251.2 * percent * scale + ',251.2');
        if (text) {
	        text.textContent = Math.round(percent * 100) + '%';
	    }
    }

	private animateProgress(percent : number, scale : number) {
        
        const progressBorder = this.circularProgressbar.querySelector('#progress-border');
        const progressInner = this.circularProgressbar.querySelector('#progress-inner');
        const text = this.circularProgressbar.querySelector('#count');
        const animationStepString = progressBorder?.getAttribute('stroke-dasharray')?.split(",")[0];
		let animationStep = 0;
		if (animationStepString) {
           	animationStep = parseInt(animationStepString);
		}
		const id = setInterval(frame, this.delay);
        const grow = animationStep < (251.2 * percent);

        function frame() {
            if (grow) {
                if (animationStep >= 251.2 * percent * scale) {
                    progressBorder?.setAttribute('stroke-dasharray', 251.2 * percent * scale + ',251.2');
                    progressInner?.setAttribute('stroke-dasharray', 251.2 * percent * scale + ',251.2');
                    if (text) {
						text.textContent = Math.round(percent * 100) + '%';
					}
                    clearInterval(id);
                } else {
                    progressBorder?.setAttribute('stroke-dasharray', animationStep + ',251.2');
                    progressInner?.setAttribute('stroke-dasharray', animationStep + ',251.2');
                    if (text) {
                      	text.textContent = Math.round(animationStep / 251.2 * 100) + '%';
    				}
                    animationStep++;
                }
            } else {
                if (animationStep < 251.2 * percent * scale) {
                    progressBorder?.setAttribute('stroke-dasharray', 251.2 * percent * scale + ',251.2');
                    progressInner?.setAttribute('stroke-dasharray', 251.2 * percent * scale + ',251.2');
                    if (text) {
	                    text.textContent = Math.round(percent * 100) + '%';
	    			}
                    clearInterval(id);
                } else {
                    progressBorder?.setAttribute('stroke-dasharray', animationStep + ',251.2');
                    progressInner?.setAttribute('stroke-dasharray', animationStep + ',251.2');
                    if (text) {
	                    text.textContent = Math.round(animationStep / 251.2 * 100) + '%';
		    		}
                    animationStep--;
                }
            }
        }
    }

	render() {
		return html`
			<svg id="animated" viewBox="0 0 100 100">
				<path id="progress-background" part="progress-background" stroke-linecap="round" stroke-width="var(--_circle-width)" stroke="var(--_circle-background-color)" fill="none" stroke-dasharray="251.2,251.2" d="M50 10 a 40 40 0 0 1 0 80 a 40 40 0 0 1 0 -80"></path>
        		<path id="progress-border" part="progress-border" stroke-linecap="round" stroke-width="var(--_circle-width)" stroke="var(--_circle-border-color)" fill="none" stroke-dasharray="0,251.2" d="M50 10 a 40 40 0 0 1 0 80 a 40 40 0 0 1 0 -80"></path>
        		<path id="progress-inner" part="progress-inner" stroke-linecap="round" stroke-width="var(--_circle-inner-width)" stroke="var(--_circle-color)" fill="none" stroke-dasharray="0,251.2" d="M50 10 a 40 40 0 0 1 0 80 a 40 40 0 0 1 0 -80"></path>
        		<text id="count" part="percent" x="50" y="50" text-anchor="middle" dy="7" font-size="var(--_percent-font-size)">${this.percent*100}%</text>
        	</svg>
            <slot name="tooltip"></slot>`;
	}

}