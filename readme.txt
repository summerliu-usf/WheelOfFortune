Wheel of Fortune Game

Description: This is a more complex version of Wheel of Fortune game, where multiple Ais can play themselves.


Development:
Various functions of the game are structured accordingly into a hierarchy. I learned a lot about how inheritance works in
practice and how to reuse code.
Example: playAll() was used in all subclasses of Game

I encountered difficulty with trying to incorporate playAll() into every subclass. Sometimes the logic did not seem
straight forward and had to be changed multiple times. I also encountered issues with AIs playing fewer than the required
number of games, which was fixed through changing the logic of the playNext() function in WOFAIGame.


I was not able to reuse WOF's code in WOF and had to copy and paste some functions. If given more time, I would try to
reuse those code and avoid having to repeatedly declare data members.

What was completed: All required functions were completed.


Test plan:
- All games were played manually to ensure they produced correct results.
- The AI game (the default player version adn multiplayer version) were played and closely looked at to ensure program ran
 accordingly and added scores.



- Test video: https://drive.google.com/file/d/1p2xbnIGYSVFUnP91U__m34K0_xVAaDr/view?usp=sharing