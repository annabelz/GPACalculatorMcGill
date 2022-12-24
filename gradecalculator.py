# formula to calculate grades
def grade(course):
    
    grades = input("Enter each assignment grade separated by a space: ")
    grade_list = grades.split( )
    for g in range (0, len(grade_list)):
        grade_list[g] = float(grade_list[g])

    weights = input("Enter each assignment weight separated by a space: ")
    weight_list = weights.split( )
    for w in range (0, len(weight_list)):
        weight_list[w] = float(weight_list[w])

    while len(grade_list) != len(weight_list):
        grades = input("Re-enter each assignment grade separated by a space: ")
        grade_list = grades.split( )
        for g in range (0, len(grade_list)):
            grade_list[g] = float(grade_list[g])

        weights = input("Re-enter each assignment weight separated by a space: ")
        weight_list = weights.split( )
        for w in range (0, len(weight_list)):
            weight_list[w] = float(weight_list[w])
        
    total = 0
    for i in range(0, len(grade_list)):
        total += grade_list[i] * weight_list[i]

    if sum(weight_list) != 1:
        total = round(total / sum(weight_list), 2)
        
        final_calculation = input("Would you like to calculate the grade needed for the final? (yes or no) ")
        if final_calculation == "yes":
            goal = input("End with an average of: ")
            final_grade = round((float(goal) - total * sum(weight_list)) / (1 - sum(weight_list)), 2)
            output = "".join(["This is your average for ", course, ": ", str(total), "%"]) + "\n" + "".join(["Minimum final grade: ", str(final_grade), "%"])
            return output

    output = "".join(["This is your average for ", course, ": ", str(total), "%"])
    return output

def cumgpa():
    cum_gpa = input("Do you want to calculate cumulative GPA? (yes/no) ")
    if cum_gpa == "yes":
        current_gpa = float(input("Enter current GPA: "))
        current_credits = float(input("Enter current total amount of credits: "))
    else:
        current_gpa = 0
        current_credits = 0
    
    grades = input("Enter each course grade separated by a space: ")
    grade_list = grades.split( )
    for g in range(0, len(grade_list)):
        if 48 <= ord(grade_list[g][0]) <= 57:
            grade_list[g] = float(grade_list[g])
        elif grade_list[g] == ("A" or "a"):
            grade_list[g] = 85
        elif grade_list[g] == ("A-" or "a-"):
            grade_list[g] = 80
        elif grade_list[g] == ("B+" or "b+"):
            grade_list[g] = 75
        elif grade_list[g] == ("B" or "b"):
            grade_list[g] = 70
        elif grade_list[g] == ("B-" or "b-"):
            grade_list[g] = 65
        elif grade_list[g] == ("C+" or "c+"):
            grade_list[g] = 60
        elif grade_list[g] == ("C" or "c"):
            grade_list[g] = 55
        elif grade_list[g] == ("D" or "d"):
            grade_list[g] = 50
        elif grade_list[g] == ("F" or "f"):
            grade_list[g] = 49

    for g in range (0, len(grade_list)):
        if grade_list[g] >= 85:
            grade_list[g] = 4.0
        elif grade_list[g] >= 80:
            grade_list[g] = 3.7
        elif grade_list[g] >= 75:
            grade_list[g] = 3.3
        elif grade_list[g] >= 70:
            grade_list[g] = 3.0
        elif grade_list[g] >= 65:
            grade_list[g] = 2.7
        elif grade_list[g] >= 60:
            grade_list[g] = 2.3
        elif grade_list[g] >= 55:
            grade_list[g] = 2.0
        elif grade_list[g] >= 50:
            grade_list[g] = 1.0
        elif grade_list[g] <= 49:
            grade_list[g] = 0
        
    credit = input("Enter amount of credits for each course separated by a space: ")
    credit_list = credit.split( )
    for c in range(0, len(credit_list)):
        credit_list[c] = float(credit_list[c])
        
    total = 0
    for i in range(0, len(grade_list)):
        total += grade_list[i] * credit_list[i]
    
    total = round((total + current_gpa * current_credits) / (current_credits + sum(credit_list)), 2)
    
    if cum_gpa == "yes":
        output = "".join(["This is your cumulative GPA: ", str(total)])
    else:
        output = "".join(["This is your term GPA: ", str(total)])
                     
    return output

print(cumgpa())
    
    