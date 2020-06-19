// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

function createMap() {
  var nigeria = {lat: 9.082, lng: 8.675};
  var ibadan = {lat: 7.377, lng: 3.947};

  const map = new google.maps.Map(
      document.getElementById('map'), 
      {center: nigeria, zoom: 5}
  );

  const cityMarker = new google.maps.Marker(
      {
        position: ibadan,
        map: map,
        title: 'Ibadan, my parents hometown!'
      }
  );
}

async function getData() {
  fetch('/data').then(response => response.json()).then((messages) => {
    const dataListElement = document.getElementById('data-blast');
    for (var index in messages) {
        dataListElement.appendChild(createListElement(messages[index]));
    }
  });
}

function addRandomQuote() {
  const quotes =
      ['Poor people don’t have time for investments because poor people are too busy trying not to be poor. - Earn (Atlanta)', 
      'I think if we spent the time we spend thinking about not spending money, spent that time on spending money, then it’d be time well spent. - Darius (Atlanta)', 
      'You have failed this city! - Oliver (Arrow)', 
      'We’re defined by our choices. - Dr. Borden (Blindspot)', 
      'It’s the quenchiest! - Sokka (Avatar: The Last Airbender)', 
      'Life happens wherever you are, whether you make it or not. - Iroh (Avatar: The Last Airbender)', 
      'No one calls Esteban Julio Ricardo Montoya de la Rosa Ramirez a thief! - Esteban (The Suite Life of Zack and Cody)', 
      'Give up? Give up?! The day may come when we’ll give up on fruitless searches after a mere 11 minutes, but that day is NOT today! The day may come when our favorite reptile may be lost from our memories and his enduring love of mushrooms forgotten, but that day is not today! Today we search! We will search for him in the streets, we will search for him in the trenches, we will search for him in the alleys and the mini-malls and the cul-de-sacs of this fair land. We will search for him in the multi-level car parks and municipal recreation facilities. And we few, we happy few, we small band of brothers - and girl from across the street. We shall not cease ’til he is found! - Ferb (Phineas and Ferb)', 
      'I’m just the keeper of secrets. Like the Vatican. - Jamal (On My Block)'];

  // Picks random quote
  const quote = quotes[Math.floor(Math.random() * quotes.length)];

  // Adds to page
  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.innerText = quote;
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
