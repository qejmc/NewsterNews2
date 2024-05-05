import React from "react";
import useState from "react";
import { TOPICS } from "../../utils/TopicList";
import { RegisterUserInfoBtn } from "../../utils/Buttons/Buttons.js";
import "./UserProfilePage.css";
import { useParams } from "react-router-dom";
import CheckboxComponent from "./CheckboxComponent";
import RadioFieldComponent from "./RadioFieldComponent";
import { OptInList } from "../../utils/RecOptIn.js";
import CheckboxRec from "./OptInCheckbox.js";
import axios from "axios";

function UserProfilePage(props) {
  function checkTopic() {
    const testElements = document.getElementsByClassName("topicOption");
    let topicsList = "";
    for (let i = 0; i < testElements.length; i++) {
      if (testElements[i].checked) {
        topicsList += i + 1 + " ";
      }
    }
    return topicsList;
  }

  function checkFreq() {
    const testElements = document.getElementsByClassName("frequencyOption");
    let freq = "";
    for (let i = 0; i < testElements.length; i++) {
      if (testElements[i].checked) {
        freq = i + 1;
        break;
      }
    }
    return freq;
  }

  function checkOptIn() {
    const testElements = document.getElementsByClassName("prefOption");
    let PrefList = "";
    for (let i = 0; i < testElements.length; i++) {
      if (testElements[i].checked) {
        PrefList += i + 1 + " ";
      }
    }
    return PrefList;
  }

  async function registerPreference() {
    let topicsList = checkTopic();
    let freq = checkFreq();
    let rec = checkOptIn();
    // console.log(topicsList);
    // console.log(freq);
    let registerInfoRequest = await axios.post(
      "http://localhost:8080/register",
      {
        token: localStorage.getItem("token"),
        topics: topicsList,
        frequency: freq,
       recommendation: rec,
      }
    );
    if (registerInfoRequest.data.Correct === "Yes") {
      alert("Successfully registered your preferences!");
    }
    localStorage.setItem("userTopics", topicsList);
    localStorage.setItem("userFrequency", freq);
   // localStorage.setItem("RecommendationOptIn", rec);
  }

  let userName = useParams().id;
  if (userName === undefined) {
    window.location.href = "/404NotFound";
    return -1; //not sure if this is dead code just making sure nothing weird happens after redirect
  }
  //verify token and username
  let userTopicsList = [];
  let userFrequency = 1;
  let userRecList = [];
  if (localStorage.getItem("userTopics") != null) {
    let userStrList = localStorage.getItem("userTopics").trim().split(" ");
    console.log(userStrList);
    for (let i = 0; i < userStrList.length; i++) {
      userTopicsList.push(parseInt(userStrList[i]) - 1);
    }
  }
  if (localStorage.getItem("userFrequency") != null) {
    userFrequency = localStorage.getItem("userFrequency");
  }

  return (
    <div>
      <div id="topLeftUserInfoID">
        Welcome back, {userName}. <br />
      </div>

      <div id="topicListID">
        <h1>TopicList</h1>

        {TOPICS.map((topicName, topicIndex) => {
          let info = {
            topicName,
            topicIndex,
            userTopicsList,
          };
          return <CheckboxComponent {...info} />;
        })}
      </div>
      
      <br />

      <RadioFieldComponent initialFrequency={userFrequency} />

      <br /> 

      <div id="optListID">
        <h1> Opt-In for Recommendations </h1>
        <p> Opting in for our recommendation subscription allows you to recieve news articles tailored to your interests.</p>
        {OptInList.map((prefName, prefIndex) => {
          let info = {
            prefName,
            prefIndex,
            userRecList,
          };
          return <CheckboxRec {...info} />;
        })}
      </div>
      


      <button
        onClick={async () => {
          await registerPreference();
        }}
      >
        Save
      </button>
    </div>
  );
}

function GenerateTopicCheckbox(topicName, topicIndex, userTopicList) {
  if (topicIndex in userTopicList) {
    return (
      <div>
        <input
          className="topicOption"
          id={topicName}
          type="checkbox"
          name={topicName}
          value="true"
          defaultChecked
        />
        <label htmlFor={topicName}>{topicName}</label>
      </div>
    );
  } else {
    return (
      <div>
        <input
          className="topicOption"
          id={topicName}
          type="checkbox"
          name={topicName}
          value="true"
        />
        <label htmlFor={topicName}>{topicName}</label>
      </div>
    );
  }
}

function GenerateFrequencyRadio(
  userFrequencySelection,
  frequencyOptionName,
  frequencyOptionIndex
) {
  //const [selectedFrequency, setSelectedFrequency] = useState(
  //  userFrequencySelection
  //);
  if (frequencyOptionIndex === userFrequencySelection) {
    return (
      <div>
        <input
          className="frequencyOption"
          type="radio"
          id={frequencyOptionName}
          name="frequency"
          value={frequencyOptionIndex + 1}
          defaultChecked
        />
        <label htmlFor={frequencyOptionName}>{frequencyOptionName}</label>
      </div>
    );
  } else {
    return (
      <div>
        <input
          className="frequencyOption"
          type="radio"
          id={frequencyOptionName}
          name="frequency"
          value={frequencyOptionIndex + 1}
        />
        <label htmlFor={frequencyOptionName}>{frequencyOptionName}</label>
      </div>
    );
  }
}

export default UserProfilePage;
