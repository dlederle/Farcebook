     <h1>Don't Have an Account?</h1>
     <form class="form-stacked" action="controllers/registerController.jsp" method = "GET">
     <div class="row">
          <div class="span4">
          <h2>Register</h2>
               <fieldset>
               <label for="userEmail">Please enter a valid e-mail address:</label>
               <div class="input">
                    <input type="text" name="userEmail" id="userEmail"/>
               </div>
          
               <label for="password">Please enter your password:</label>
               <div class="input">
                    <input type="password" name="password" id="password"/>
               </div>
    	     
               <label for="password2">Confirm password: </label>
               <div class="input">
                    <input type="password" name="password2" id=password2" />	
               </div>
          </div>
          <div class="span6 offset4">
          <h2>Now please fill out this additional information:</h2>
          <label for="firstName">First Name: </label>
          <div class="input">
               <input type="text" name="firstName" id="firstName"/>
          </div>

          <label for="lastName">Last Name: </label>
          <div class="input">
               <input type="text" name="lastName" id="lastName"/>
          </div>

          <label for="address">Home Address: </label>
          <div class="input">
               <input type="text" name="address" id="address"/>
          </div>
          <label for="alias">Nickname: </label>
          <div class="input">
               <input type="text" name="alias" id="alias"/>
          </div>

          <label for="ssn">Social Security Number: </label>
          <div class="input" id="ssn">
               <input class="span1" type="password" size="3" maxlength="3" name="ssn1" />
          -
               <input class="span1" type="password" size="2" maxlength="2" name="ssn2" />
          -
               <input class="span1" type="password" size="4" maxlength="4" name="ssn3" />
          </div> 

          <label for="maidenName">Mother's Maiden Name: </label>
          <div class="input">
               <input type="text" name="maidenName" id="maidenName"/>
          </div>

          <label for="income">Current Annual Income: </label>
          <div class="input">
               <select class="medium" name="income" id="income">
               <option value="1">$0-10,000</option>
               <option value="2">$10,000-25,000</option>
               <option value="3">$25,000-50,000</option>
               <option value="4">$50,000-100,000</option>
               <option value="5">$100,000-250,000</option>
               <option value="6">$250,000-500,000</option>
               <option value="7">$500,000+</option>
          </select>
          </div>
          <input class="btn primary" type="submit" name="submit" value="Register">
          </fieldset>
          </div>
     </div>
     </form>
