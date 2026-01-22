# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**
- What is the correct unit of cost tracking, given that a single operation may include multiple subtasks of different scale and granularity?
- Costs should be traceable across all participants (Warehouses, Stores, external parties) to fairly allocate expenses.
- How should costs be attributed to each participant to reflect actual involvement?
- How to handle timing differences between operational events and financial cost availability, including late or adjusted data?

## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**
- Which reports and aggregation levels are needed to identify key cost drivers (per operation, item, warehouse, time period)?
- Collected data must be validated to ensure accuracy and reliability for decision-making.
- Which inefficiencies can be addressed purely through analytics without major process changes?
- What optimization strategies can be implemented via existing IT system rules, automation, or scheduled processes?
- How can cost optimization initiatives be prioritized based on measurable impact and implementation effort?
- How will performance be measured and compared before and after optimization to validate results?

## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**
- Which operational cost data must flow into financial systems to support accurate accounting, budgeting, and reporting?
- Timely availability of this data affects operational and financial decisions; missing or delayed data creates risks.
- How will integrated, accurate data improve decision-making and operational efficiency?
- Which KPIs and reporting processes benefit most from real-time synchronization?
- Integration reduces errors, delays, and manual reconciliation.
- To ensure seamless integration: implement message queues and backend synchronization to persist operational events.
- Failures in integration should not block core processes; provide automatic retries, logging, and monitoring to maintain reliability.

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**
- What historical cost and operational data is required to build accurate forecasts?
- Forecasting must account for seasonality, demand fluctuations, and special events to predict resource needs correctly.
- Which drivers (labor, inventory, transportation, overhead) have the most impact on future costs?
- How frequently should budgets and forecasts be updated to remain relevant for operational planning?
- How to validate forecasting accuracy and adjust models over time based on real outcomes?
- What reporting and visualization capabilities are needed to make forecasts actionable for managers?
- The system should support allocation of resources according to forecasted costs while maintaining flexibility for unexpected changes.

## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**
- Preserving the cost history of the old Warehouse is critical to maintain accurate budget comparisons and track efficiency trends.
  * How should historical costs be archived and linked to the new Warehouse?
- During the transition, ongoing operations and shared resources must be managed carefully.
  * How to handle open orders and allocate shared costs correctly?
- Budget planning for the new Warehouse must reflect historical variances.
  * Which historical patterns and KPIs should influence the new budget?
- Cost control processes should continue uninterrupted.
  * What reconciliation and validation steps are needed to ensure continuity?
- Reports and KPIs may need to include combined historical data for managerial decisions.
  * How should reporting reflect both old and new Warehouse data for clear insights?

## Instructions for Candidates
Before starting the case study, read the [BRIEFING.md](BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
