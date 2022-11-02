import { css, svg, LitElement } from 'lit';
import { customElement, property, query } from 'lit/decorators.js';

@customElement('circular-progress-bar')
export class CircularProgressBar extends LitElement {

	@property()
	scale = 1.0;

	@property()
	percent = 0;

	@property()
	label : string | null = null;

  	@property({reflect: true})
  	animation : boolean | undefined = undefined;

	@query("#animated")
	circularProgressbar! : SVGElement;

	static get styles() {
    	return css`
    		svg #count {
        		fill: var(--lumo-primary-text-color);
    		}
    		svg #label {
        		fill: var(--lumo-secondary-text-color);
    		}
		`;
	}

	updated() {
		if (this.label) {
			this.updateLabel(this.label);
		}
		this.animateProgress(this.percent, this.scale);
	}

	updateLabel(labelText : string) : void {
        const countElement = this.circularProgressbar.querySelector('#count');
        countElement?.setAttribute('y', '43');
        const labelElement = this.circularProgressbar.querySelector('#label');
        if (!labelElement) {
            var svg = this.circularProgressbar.querySelector('#animated');
            this.circularProgressbar.innerHTML = this.circularProgressbar.innerHTML + '<text id="label" x="50" y="59" text-anchor="middle" dy="7" font-size="11">' + labelText + '</text>';
        } else {
            labelElement.textContent = labelText;
        }
    }
	
	animateProgress(percent : number, scale : number) {
        
        const progressBorder = this.circularProgressbar.querySelector('#progress-border');
        const progressInner = this.circularProgressbar.querySelector('#progress-inner');
        const text = this.circularProgressbar.querySelector('#count');
        const animationStepString = progressBorder?.getAttribute('stroke-dasharray')?.split(",")[0];
		let animationStep = 0;
		if (animationStepString) {
           	animationStep = parseInt(animationStepString);
		}
		const id = setInterval(frame, 5);
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
		return svg`
			<svg id="animated" viewBox="0 0 100 100">
				<path id="progress-background" class="circular-progressbar-circle-part" stroke-linecap="round" stroke-width="6" stroke="var(--lumo-contrast-20pct)" fill="none" stroke-dasharray="251.2,251.2" d="M50 10 a 40 40 0 0 1 0 80 a 40 40 0 0 1 0 -80"></path>
        		<path id="progress-border" class="circular-progressbar-circle-part" stroke-linecap="round" stroke-width="6" stroke="var(--lumo-contrast)" fill="none" stroke-dasharray="0,251.2" d="M50 10 a 40 40 0 0 1 0 80 a 40 40 0 0 1 0 -80"></path>
        		<path id="progress-inner" class="circular-progressbar-circle-part" stroke-linecap="round" stroke-width="4" stroke="var(--lumo-primary-color)" fill="none" stroke-dasharray="0,251.2" d="M50 10 a 40 40 0 0 1 0 80 a 40 40 0 0 1 0 -80"></path>
        		<text id="count" x="50" y="50" text-anchor="middle" dy="7" font-size="17">${this.percent*100}%</text>
        	</svg>`;
	}

}