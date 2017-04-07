In this code, I refactor the following code
```
	//reset the animation after we stop the animation
	private void animationReset(int cycle) {
		animation.stop();
		animation.getKeyFrames().clear();
		frameSetUp(delay, cycle);
	}
	```
	This code snippet is used multiple times in the main class. The olly difference is the change of the parameter, so I can refactor the code.
	
	```
		private void setButtonAction(Set<String> buttonNames, Stage stage) {
		for(String s : buttonNames){
			if(s.equals(myResources.getString("stepforward"))){
				
				u.getButton().get(s).setOnAction(e -> stepForward());
				
			}else if(s.equals(myResources.getString("resume"))){
				
				u.getButton().get(s).setOnAction(e -> resume());
				
			}else if(s.equals(myResources.getString("pause"))){
				
				u.getButton().get(s).setOnAction(e -> pause());
				
			}else if(s.equals(myResources.getString("speedup"))){
				
				u.getButton().get(s).setOnAction(e -> speedUp());
				
			}else if(s.equals(myResources.getString("slowdown"))){
				
				u.getButton().get(s).setOnAction(e -> slowDown());
				
			}else if(s.equals(myResources.getString("fileselection"))){
				u.getButton().get(s).setOnAction(e -> select(stage));
			}
				
		}
		
	}
```
For this part of the code , I think this is the best way to write the code in terms of what I wrote for other parts.