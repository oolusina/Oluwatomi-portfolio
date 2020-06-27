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

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.lang.Integer;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ArrayList<TimeRange> available = new ArrayList<TimeRange>();

    //null check and checks if request is longer than a day
    if (request == null || request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
        return available;
    } //null check and checks if there are no events or no attendees in request
    else if (events == null || events == (Collection)Collections.emptySet() || 
        request.getAttendees() == (Collection)Collections.emptySet()) {
            return Arrays.asList(TimeRange.WHOLE_DAY);
    }
    
    //TreeSet to maintain all blocked TimeRange's without duplicates
    TreeSet<TimeRange> blockedTimes = new TreeSet<TimeRange>(TimeRange.ORDER_BY_START);
    for (Event e: events) {
        for (String a: request.getAttendees()) {
            if (e.getAttendees().contains(a)) blockedTimes.add(e.getWhen());
        }
    }
    
    int start = 0;
    while (!blockedTimes.isEmpty()) { //Going through all blocked times
        TimeRange current = blockedTimes.pollFirst();
        //This check makes sure the current free start time is not contained within this 
        //current block or behind the end of this current block
        if (!current.contains(start) && start < current.start()) {
            int end = current.start();
            //if there is time between the current free start time and the beginning of this
            //block, then the free period is added to the available ArrayList
            if ((end - start) >= request.getDuration()) {
                available.add(TimeRange.fromStartEnd(start, end, false));
            }
        }
        //Set new free start time to end of current block
        start = Integer.max(current.end(), start);
    }
    //Adds end of day free block if possible
    if ((TimeRange.END_OF_DAY - start) >= request.getDuration()) {
        available.add(TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY, true));
    }

    return available;
  }
}
